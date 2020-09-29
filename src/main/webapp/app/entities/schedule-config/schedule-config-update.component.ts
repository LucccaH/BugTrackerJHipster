import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IScheduleConfig, ScheduleConfig } from 'app/shared/model/schedule-config.model';
import { ScheduleConfigService } from './schedule-config.service';
import { ICycleConfig } from 'app/shared/model/cycle-config.model';
import { CycleConfigService } from 'app/entities/cycle-config/cycle-config.service';

@Component({
  selector: 'jhi-schedule-config-update',
  templateUrl: './schedule-config-update.component.html',
})
export class ScheduleConfigUpdateComponent implements OnInit {
  isSaving = false;
  schedulecycleconfigs: ICycleConfig[] = [];

  editForm = this.fb.group({
    id: [],
    privilige: [],
    retryTime: [],
    retryInterval: [],
    scheduleCycleConfigId: [],
  });

  constructor(
    protected scheduleConfigService: ScheduleConfigService,
    protected cycleConfigService: CycleConfigService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleConfig }) => {
      this.updateForm(scheduleConfig);

      this.cycleConfigService
        .query({ filter: 'scheduleconfig-is-null' })
        .pipe(
          map((res: HttpResponse<ICycleConfig[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICycleConfig[]) => {
          if (!scheduleConfig.scheduleCycleConfigId) {
            this.schedulecycleconfigs = resBody;
          } else {
            this.cycleConfigService
              .find(scheduleConfig.scheduleCycleConfigId)
              .pipe(
                map((subRes: HttpResponse<ICycleConfig>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICycleConfig[]) => (this.schedulecycleconfigs = concatRes));
          }
        });
    });
  }

  updateForm(scheduleConfig: IScheduleConfig): void {
    this.editForm.patchValue({
      id: scheduleConfig.id,
      privilige: scheduleConfig.privilige,
      retryTime: scheduleConfig.retryTime,
      retryInterval: scheduleConfig.retryInterval,
      scheduleCycleConfigId: scheduleConfig.scheduleCycleConfigId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scheduleConfig = this.createFromForm();
    if (scheduleConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleConfigService.update(scheduleConfig));
    } else {
      this.subscribeToSaveResponse(this.scheduleConfigService.create(scheduleConfig));
    }
  }

  private createFromForm(): IScheduleConfig {
    return {
      ...new ScheduleConfig(),
      id: this.editForm.get(['id'])!.value,
      privilige: this.editForm.get(['privilige'])!.value,
      retryTime: this.editForm.get(['retryTime'])!.value,
      retryInterval: this.editForm.get(['retryInterval'])!.value,
      scheduleCycleConfigId: this.editForm.get(['scheduleCycleConfigId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleConfig>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICycleConfig): any {
    return item.id;
  }
}

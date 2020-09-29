import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICycleConfig, CycleConfig } from 'app/shared/model/cycle-config.model';
import { CycleConfigService } from './cycle-config.service';
import { IDependencyCondition } from 'app/shared/model/dependency-condition.model';
import { DependencyConditionService } from 'app/entities/dependency-condition/dependency-condition.service';

@Component({
  selector: 'jhi-cycle-config-update',
  templateUrl: './cycle-config-update.component.html',
})
export class CycleConfigUpdateComponent implements OnInit {
  isSaving = false;
  conditionroots: IDependencyCondition[] = [];
  effectiveDateDp: any;
  assignedDateDp: any;
  assignedTimeDp: any;

  editForm = this.fb.group({
    id: [],
    effectiveDate: [],
    assignedDate: [],
    assignedTime: [],
    cycleEnum: [],
    conditionRootId: [null, Validators.required],
  });

  constructor(
    protected cycleConfigService: CycleConfigService,
    protected dependencyConditionService: DependencyConditionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cycleConfig }) => {
      this.updateForm(cycleConfig);

      this.dependencyConditionService
        .query({ filter: 'cycleconfig-is-null' })
        .pipe(
          map((res: HttpResponse<IDependencyCondition[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDependencyCondition[]) => {
          if (!cycleConfig.conditionRootId) {
            this.conditionroots = resBody;
          } else {
            this.dependencyConditionService
              .find(cycleConfig.conditionRootId)
              .pipe(
                map((subRes: HttpResponse<IDependencyCondition>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDependencyCondition[]) => (this.conditionroots = concatRes));
          }
        });
    });
  }

  updateForm(cycleConfig: ICycleConfig): void {
    this.editForm.patchValue({
      id: cycleConfig.id,
      effectiveDate: cycleConfig.effectiveDate,
      assignedDate: cycleConfig.assignedDate,
      assignedTime: cycleConfig.assignedTime,
      cycleEnum: cycleConfig.cycleEnum,
      conditionRootId: cycleConfig.conditionRootId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cycleConfig = this.createFromForm();
    if (cycleConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.cycleConfigService.update(cycleConfig));
    } else {
      this.subscribeToSaveResponse(this.cycleConfigService.create(cycleConfig));
    }
  }

  private createFromForm(): ICycleConfig {
    return {
      ...new CycleConfig(),
      id: this.editForm.get(['id'])!.value,
      effectiveDate: this.editForm.get(['effectiveDate'])!.value,
      assignedDate: this.editForm.get(['assignedDate'])!.value,
      assignedTime: this.editForm.get(['assignedTime'])!.value,
      cycleEnum: this.editForm.get(['cycleEnum'])!.value,
      conditionRootId: this.editForm.get(['conditionRootId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICycleConfig>>): void {
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

  trackById(index: number, item: IDependencyCondition): any {
    return item.id;
  }
}

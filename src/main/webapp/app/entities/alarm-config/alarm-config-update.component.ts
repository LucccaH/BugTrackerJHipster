import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlarmConfig, AlarmConfig } from 'app/shared/model/alarm-config.model';
import { AlarmConfigService } from './alarm-config.service';

@Component({
  selector: 'jhi-alarm-config-update',
  templateUrl: './alarm-config-update.component.html',
})
export class AlarmConfigUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    alarmMethodb: [],
    accountInfo: [],
  });

  constructor(protected alarmConfigService: AlarmConfigService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alarmConfig }) => {
      this.updateForm(alarmConfig);
    });
  }

  updateForm(alarmConfig: IAlarmConfig): void {
    this.editForm.patchValue({
      id: alarmConfig.id,
      alarmMethodb: alarmConfig.alarmMethodb,
      accountInfo: alarmConfig.accountInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alarmConfig = this.createFromForm();
    if (alarmConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.alarmConfigService.update(alarmConfig));
    } else {
      this.subscribeToSaveResponse(this.alarmConfigService.create(alarmConfig));
    }
  }

  private createFromForm(): IAlarmConfig {
    return {
      ...new AlarmConfig(),
      id: this.editForm.get(['id'])!.value,
      alarmMethodb: this.editForm.get(['alarmMethodb'])!.value,
      accountInfo: this.editForm.get(['accountInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarmConfig>>): void {
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
}

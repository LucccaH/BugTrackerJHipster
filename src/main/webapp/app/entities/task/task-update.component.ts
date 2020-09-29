import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { IAlarmConfig } from 'app/shared/model/alarm-config.model';
import { AlarmConfigService } from 'app/entities/alarm-config/alarm-config.service';
import { IScheduleConfig } from 'app/shared/model/schedule-config.model';
import { ScheduleConfigService } from 'app/entities/schedule-config/schedule-config.service';

type SelectableEntity = IAlarmConfig | IScheduleConfig;

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  alarmconfigs: IAlarmConfig[] = [];
  scheduleconfigs: IScheduleConfig[] = [];
  createTimeDp: any;

  editForm = this.fb.group({
    id: [],
    taskVersionId: [null, [Validators.required]],
    taskDetail: [],
    taskName: [],
    dataType: [],
    taskStatusEnum: [],
    createTime: [],
    alarmConfigId: [],
    scheduleConfigId: [null, Validators.required],
  });

  constructor(
    protected taskService: TaskService,
    protected alarmConfigService: AlarmConfigService,
    protected scheduleConfigService: ScheduleConfigService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      this.updateForm(task);

      this.alarmConfigService
        .query({ filter: 'task-is-null' })
        .pipe(
          map((res: HttpResponse<IAlarmConfig[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAlarmConfig[]) => {
          if (!task.alarmConfigId) {
            this.alarmconfigs = resBody;
          } else {
            this.alarmConfigService
              .find(task.alarmConfigId)
              .pipe(
                map((subRes: HttpResponse<IAlarmConfig>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAlarmConfig[]) => (this.alarmconfigs = concatRes));
          }
        });

      this.scheduleConfigService
        .query({ filter: 'task-is-null' })
        .pipe(
          map((res: HttpResponse<IScheduleConfig[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IScheduleConfig[]) => {
          if (!task.scheduleConfigId) {
            this.scheduleconfigs = resBody;
          } else {
            this.scheduleConfigService
              .find(task.scheduleConfigId)
              .pipe(
                map((subRes: HttpResponse<IScheduleConfig>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IScheduleConfig[]) => (this.scheduleconfigs = concatRes));
          }
        });
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      taskVersionId: task.taskVersionId,
      taskDetail: task.taskDetail,
      taskName: task.taskName,
      dataType: task.dataType,
      taskStatusEnum: task.taskStatusEnum,
      createTime: task.createTime,
      alarmConfigId: task.alarmConfigId,
      scheduleConfigId: task.scheduleConfigId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id'])!.value,
      taskVersionId: this.editForm.get(['taskVersionId'])!.value,
      taskDetail: this.editForm.get(['taskDetail'])!.value,
      taskName: this.editForm.get(['taskName'])!.value,
      dataType: this.editForm.get(['dataType'])!.value,
      taskStatusEnum: this.editForm.get(['taskStatusEnum'])!.value,
      createTime: this.editForm.get(['createTime'])!.value,
      alarmConfigId: this.editForm.get(['alarmConfigId'])!.value,
      scheduleConfigId: this.editForm.get(['scheduleConfigId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

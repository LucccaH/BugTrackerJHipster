import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScheduleConfig } from 'app/shared/model/schedule-config.model';
import { ScheduleConfigService } from './schedule-config.service';

@Component({
  templateUrl: './schedule-config-delete-dialog.component.html',
})
export class ScheduleConfigDeleteDialogComponent {
  scheduleConfig?: IScheduleConfig;

  constructor(
    protected scheduleConfigService: ScheduleConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scheduleConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast('scheduleConfigListModification');
      this.activeModal.close();
    });
  }
}

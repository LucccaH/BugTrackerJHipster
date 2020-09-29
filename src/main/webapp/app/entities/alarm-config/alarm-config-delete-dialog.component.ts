import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlarmConfig } from 'app/shared/model/alarm-config.model';
import { AlarmConfigService } from './alarm-config.service';

@Component({
  templateUrl: './alarm-config-delete-dialog.component.html',
})
export class AlarmConfigDeleteDialogComponent {
  alarmConfig?: IAlarmConfig;

  constructor(
    protected alarmConfigService: AlarmConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alarmConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alarmConfigListModification');
      this.activeModal.close();
    });
  }
}

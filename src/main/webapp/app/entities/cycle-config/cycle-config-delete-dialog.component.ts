import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICycleConfig } from 'app/shared/model/cycle-config.model';
import { CycleConfigService } from './cycle-config.service';

@Component({
  templateUrl: './cycle-config-delete-dialog.component.html',
})
export class CycleConfigDeleteDialogComponent {
  cycleConfig?: ICycleConfig;

  constructor(
    protected cycleConfigService: CycleConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cycleConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cycleConfigListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDependencyCondition } from 'app/shared/model/dependency-condition.model';
import { DependencyConditionService } from './dependency-condition.service';

@Component({
  templateUrl: './dependency-condition-delete-dialog.component.html',
})
export class DependencyConditionDeleteDialogComponent {
  dependencyCondition?: IDependencyCondition;

  constructor(
    protected dependencyConditionService: DependencyConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dependencyConditionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dependencyConditionListModification');
      this.activeModal.close();
    });
  }
}

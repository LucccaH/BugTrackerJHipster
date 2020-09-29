import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDependencyCondition } from 'app/shared/model/dependency-condition.model';
import { DependencyConditionService } from './dependency-condition.service';
import { DependencyConditionDeleteDialogComponent } from './dependency-condition-delete-dialog.component';

@Component({
  selector: 'jhi-dependency-condition',
  templateUrl: './dependency-condition.component.html',
})
export class DependencyConditionComponent implements OnInit, OnDestroy {
  dependencyConditions?: IDependencyCondition[];
  eventSubscriber?: Subscription;

  constructor(
    protected dependencyConditionService: DependencyConditionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.dependencyConditionService
      .query()
      .subscribe((res: HttpResponse<IDependencyCondition[]>) => (this.dependencyConditions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDependencyConditions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDependencyCondition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDependencyConditions(): void {
    this.eventSubscriber = this.eventManager.subscribe('dependencyConditionListModification', () => this.loadAll());
  }

  delete(dependencyCondition: IDependencyCondition): void {
    const modalRef = this.modalService.open(DependencyConditionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dependencyCondition = dependencyCondition;
  }
}

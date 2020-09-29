import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICycleConfig } from 'app/shared/model/cycle-config.model';
import { CycleConfigService } from './cycle-config.service';
import { CycleConfigDeleteDialogComponent } from './cycle-config-delete-dialog.component';

@Component({
  selector: 'jhi-cycle-config',
  templateUrl: './cycle-config.component.html',
})
export class CycleConfigComponent implements OnInit, OnDestroy {
  cycleConfigs?: ICycleConfig[];
  eventSubscriber?: Subscription;

  constructor(
    protected cycleConfigService: CycleConfigService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.cycleConfigService.query().subscribe((res: HttpResponse<ICycleConfig[]>) => (this.cycleConfigs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCycleConfigs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICycleConfig): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCycleConfigs(): void {
    this.eventSubscriber = this.eventManager.subscribe('cycleConfigListModification', () => this.loadAll());
  }

  delete(cycleConfig: ICycleConfig): void {
    const modalRef = this.modalService.open(CycleConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cycleConfig = cycleConfig;
  }
}

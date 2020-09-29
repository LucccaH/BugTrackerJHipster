import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IScheduleConfig } from 'app/shared/model/schedule-config.model';
import { ScheduleConfigService } from './schedule-config.service';
import { ScheduleConfigDeleteDialogComponent } from './schedule-config-delete-dialog.component';

@Component({
  selector: 'jhi-schedule-config',
  templateUrl: './schedule-config.component.html',
})
export class ScheduleConfigComponent implements OnInit, OnDestroy {
  scheduleConfigs?: IScheduleConfig[];
  eventSubscriber?: Subscription;

  constructor(
    protected scheduleConfigService: ScheduleConfigService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.scheduleConfigService.query().subscribe((res: HttpResponse<IScheduleConfig[]>) => (this.scheduleConfigs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInScheduleConfigs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IScheduleConfig): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInScheduleConfigs(): void {
    this.eventSubscriber = this.eventManager.subscribe('scheduleConfigListModification', () => this.loadAll());
  }

  delete(scheduleConfig: IScheduleConfig): void {
    const modalRef = this.modalService.open(ScheduleConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.scheduleConfig = scheduleConfig;
  }
}

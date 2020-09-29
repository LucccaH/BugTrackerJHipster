import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlarmConfig } from 'app/shared/model/alarm-config.model';
import { AlarmConfigService } from './alarm-config.service';
import { AlarmConfigDeleteDialogComponent } from './alarm-config-delete-dialog.component';

@Component({
  selector: 'jhi-alarm-config',
  templateUrl: './alarm-config.component.html',
})
export class AlarmConfigComponent implements OnInit, OnDestroy {
  alarmConfigs?: IAlarmConfig[];
  eventSubscriber?: Subscription;

  constructor(
    protected alarmConfigService: AlarmConfigService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.alarmConfigService.query().subscribe((res: HttpResponse<IAlarmConfig[]>) => (this.alarmConfigs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlarmConfigs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlarmConfig): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlarmConfigs(): void {
    this.eventSubscriber = this.eventManager.subscribe('alarmConfigListModification', () => this.loadAll());
  }

  delete(alarmConfig: IAlarmConfig): void {
    const modalRef = this.modalService.open(AlarmConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alarmConfig = alarmConfig;
  }
}

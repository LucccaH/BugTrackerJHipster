import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduleConfig } from 'app/shared/model/schedule-config.model';

@Component({
  selector: 'jhi-schedule-config-detail',
  templateUrl: './schedule-config-detail.component.html',
})
export class ScheduleConfigDetailComponent implements OnInit {
  scheduleConfig: IScheduleConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleConfig }) => (this.scheduleConfig = scheduleConfig));
  }

  previousState(): void {
    window.history.back();
  }
}

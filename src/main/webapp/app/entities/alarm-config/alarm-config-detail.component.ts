import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlarmConfig } from 'app/shared/model/alarm-config.model';

@Component({
  selector: 'jhi-alarm-config-detail',
  templateUrl: './alarm-config-detail.component.html',
})
export class AlarmConfigDetailComponent implements OnInit {
  alarmConfig: IAlarmConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alarmConfig }) => (this.alarmConfig = alarmConfig));
  }

  previousState(): void {
    window.history.back();
  }
}

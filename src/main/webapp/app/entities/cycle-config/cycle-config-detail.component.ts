import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICycleConfig } from 'app/shared/model/cycle-config.model';

@Component({
  selector: 'jhi-cycle-config-detail',
  templateUrl: './cycle-config-detail.component.html',
})
export class CycleConfigDetailComponent implements OnInit {
  cycleConfig: ICycleConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cycleConfig }) => (this.cycleConfig = cycleConfig));
  }

  previousState(): void {
    window.history.back();
  }
}

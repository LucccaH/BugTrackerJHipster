import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDependencyCondition } from 'app/shared/model/dependency-condition.model';

@Component({
  selector: 'jhi-dependency-condition-detail',
  templateUrl: './dependency-condition-detail.component.html',
})
export class DependencyConditionDetailComponent implements OnInit {
  dependencyCondition: IDependencyCondition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependencyCondition }) => (this.dependencyCondition = dependencyCondition));
  }

  previousState(): void {
    window.history.back();
  }
}

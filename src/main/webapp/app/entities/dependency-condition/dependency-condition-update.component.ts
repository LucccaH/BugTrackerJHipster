import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDependencyCondition, DependencyCondition } from 'app/shared/model/dependency-condition.model';
import { DependencyConditionService } from './dependency-condition.service';

@Component({
  selector: 'jhi-dependency-condition-update',
  templateUrl: './dependency-condition-update.component.html',
})
export class DependencyConditionUpdateComponent implements OnInit {
  isSaving = false;
  dependencyconditions: IDependencyCondition[] = [];
  assignedTimeDp: any;

  editForm = this.fb.group({
    id: [],
    dependencyType: [],
    flagFiekdId: [],
    assignedTime: [],
    operator: [],
    rootId: [],
  });

  constructor(
    protected dependencyConditionService: DependencyConditionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependencyCondition }) => {
      this.updateForm(dependencyCondition);

      this.dependencyConditionService
        .query()
        .subscribe((res: HttpResponse<IDependencyCondition[]>) => (this.dependencyconditions = res.body || []));
    });
  }

  updateForm(dependencyCondition: IDependencyCondition): void {
    this.editForm.patchValue({
      id: dependencyCondition.id,
      dependencyType: dependencyCondition.dependencyType,
      flagFiekdId: dependencyCondition.flagFiekdId,
      assignedTime: dependencyCondition.assignedTime,
      operator: dependencyCondition.operator,
      rootId: dependencyCondition.rootId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dependencyCondition = this.createFromForm();
    if (dependencyCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.dependencyConditionService.update(dependencyCondition));
    } else {
      this.subscribeToSaveResponse(this.dependencyConditionService.create(dependencyCondition));
    }
  }

  private createFromForm(): IDependencyCondition {
    return {
      ...new DependencyCondition(),
      id: this.editForm.get(['id'])!.value,
      dependencyType: this.editForm.get(['dependencyType'])!.value,
      flagFiekdId: this.editForm.get(['flagFiekdId'])!.value,
      assignedTime: this.editForm.get(['assignedTime'])!.value,
      operator: this.editForm.get(['operator'])!.value,
      rootId: this.editForm.get(['rootId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDependencyCondition>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IDependencyCondition): any {
    return item.id;
  }
}

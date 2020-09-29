import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDemoSharedModule } from 'app/shared/shared.module';
import { DependencyConditionComponent } from './dependency-condition.component';
import { DependencyConditionDetailComponent } from './dependency-condition-detail.component';
import { DependencyConditionUpdateComponent } from './dependency-condition-update.component';
import { DependencyConditionDeleteDialogComponent } from './dependency-condition-delete-dialog.component';
import { dependencyConditionRoute } from './dependency-condition.route';

@NgModule({
  imports: [JDemoSharedModule, RouterModule.forChild(dependencyConditionRoute)],
  declarations: [
    DependencyConditionComponent,
    DependencyConditionDetailComponent,
    DependencyConditionUpdateComponent,
    DependencyConditionDeleteDialogComponent,
  ],
  entryComponents: [DependencyConditionDeleteDialogComponent],
})
export class JDemoDependencyConditionModule {}

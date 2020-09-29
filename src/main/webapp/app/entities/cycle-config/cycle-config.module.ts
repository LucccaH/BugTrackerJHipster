import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDemoSharedModule } from 'app/shared/shared.module';
import { CycleConfigComponent } from './cycle-config.component';
import { CycleConfigDetailComponent } from './cycle-config-detail.component';
import { CycleConfigUpdateComponent } from './cycle-config-update.component';
import { CycleConfigDeleteDialogComponent } from './cycle-config-delete-dialog.component';
import { cycleConfigRoute } from './cycle-config.route';

@NgModule({
  imports: [JDemoSharedModule, RouterModule.forChild(cycleConfigRoute)],
  declarations: [CycleConfigComponent, CycleConfigDetailComponent, CycleConfigUpdateComponent, CycleConfigDeleteDialogComponent],
  entryComponents: [CycleConfigDeleteDialogComponent],
})
export class JDemoCycleConfigModule {}

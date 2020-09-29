import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDemoSharedModule } from 'app/shared/shared.module';
import { ScheduleConfigComponent } from './schedule-config.component';
import { ScheduleConfigDetailComponent } from './schedule-config-detail.component';
import { ScheduleConfigUpdateComponent } from './schedule-config-update.component';
import { ScheduleConfigDeleteDialogComponent } from './schedule-config-delete-dialog.component';
import { scheduleConfigRoute } from './schedule-config.route';

@NgModule({
  imports: [JDemoSharedModule, RouterModule.forChild(scheduleConfigRoute)],
  declarations: [
    ScheduleConfigComponent,
    ScheduleConfigDetailComponent,
    ScheduleConfigUpdateComponent,
    ScheduleConfigDeleteDialogComponent,
  ],
  entryComponents: [ScheduleConfigDeleteDialogComponent],
})
export class JDemoScheduleConfigModule {}

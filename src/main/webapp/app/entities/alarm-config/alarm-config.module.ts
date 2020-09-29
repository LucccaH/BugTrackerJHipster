import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDemoSharedModule } from 'app/shared/shared.module';
import { AlarmConfigComponent } from './alarm-config.component';
import { AlarmConfigDetailComponent } from './alarm-config-detail.component';
import { AlarmConfigUpdateComponent } from './alarm-config-update.component';
import { AlarmConfigDeleteDialogComponent } from './alarm-config-delete-dialog.component';
import { alarmConfigRoute } from './alarm-config.route';

@NgModule({
  imports: [JDemoSharedModule, RouterModule.forChild(alarmConfigRoute)],
  declarations: [AlarmConfigComponent, AlarmConfigDetailComponent, AlarmConfigUpdateComponent, AlarmConfigDeleteDialogComponent],
  entryComponents: [AlarmConfigDeleteDialogComponent],
})
export class JDemoAlarmConfigModule {}

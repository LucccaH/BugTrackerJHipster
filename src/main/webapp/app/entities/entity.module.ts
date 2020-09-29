import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.JDemoTaskModule),
      },
      {
        path: 'alarm-config',
        loadChildren: () => import('./alarm-config/alarm-config.module').then(m => m.JDemoAlarmConfigModule),
      },
      {
        path: 'schedule-config',
        loadChildren: () => import('./schedule-config/schedule-config.module').then(m => m.JDemoScheduleConfigModule),
      },
      {
        path: 'cycle-config',
        loadChildren: () => import('./cycle-config/cycle-config.module').then(m => m.JDemoCycleConfigModule),
      },
      {
        path: 'dependency-condition',
        loadChildren: () => import('./dependency-condition/dependency-condition.module').then(m => m.JDemoDependencyConditionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JDemoEntityModule {}

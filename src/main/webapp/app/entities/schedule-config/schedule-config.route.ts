import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScheduleConfig, ScheduleConfig } from 'app/shared/model/schedule-config.model';
import { ScheduleConfigService } from './schedule-config.service';
import { ScheduleConfigComponent } from './schedule-config.component';
import { ScheduleConfigDetailComponent } from './schedule-config-detail.component';
import { ScheduleConfigUpdateComponent } from './schedule-config-update.component';

@Injectable({ providedIn: 'root' })
export class ScheduleConfigResolve implements Resolve<IScheduleConfig> {
  constructor(private service: ScheduleConfigService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScheduleConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scheduleConfig: HttpResponse<ScheduleConfig>) => {
          if (scheduleConfig.body) {
            return of(scheduleConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ScheduleConfig());
  }
}

export const scheduleConfigRoute: Routes = [
  {
    path: '',
    component: ScheduleConfigComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.scheduleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScheduleConfigDetailComponent,
    resolve: {
      scheduleConfig: ScheduleConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.scheduleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScheduleConfigUpdateComponent,
    resolve: {
      scheduleConfig: ScheduleConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.scheduleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScheduleConfigUpdateComponent,
    resolve: {
      scheduleConfig: ScheduleConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.scheduleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

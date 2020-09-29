import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICycleConfig, CycleConfig } from 'app/shared/model/cycle-config.model';
import { CycleConfigService } from './cycle-config.service';
import { CycleConfigComponent } from './cycle-config.component';
import { CycleConfigDetailComponent } from './cycle-config-detail.component';
import { CycleConfigUpdateComponent } from './cycle-config-update.component';

@Injectable({ providedIn: 'root' })
export class CycleConfigResolve implements Resolve<ICycleConfig> {
  constructor(private service: CycleConfigService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICycleConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cycleConfig: HttpResponse<CycleConfig>) => {
          if (cycleConfig.body) {
            return of(cycleConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CycleConfig());
  }
}

export const cycleConfigRoute: Routes = [
  {
    path: '',
    component: CycleConfigComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.cycleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CycleConfigDetailComponent,
    resolve: {
      cycleConfig: CycleConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.cycleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CycleConfigUpdateComponent,
    resolve: {
      cycleConfig: CycleConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.cycleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CycleConfigUpdateComponent,
    resolve: {
      cycleConfig: CycleConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.cycleConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

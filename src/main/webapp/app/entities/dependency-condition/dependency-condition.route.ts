import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDependencyCondition, DependencyCondition } from 'app/shared/model/dependency-condition.model';
import { DependencyConditionService } from './dependency-condition.service';
import { DependencyConditionComponent } from './dependency-condition.component';
import { DependencyConditionDetailComponent } from './dependency-condition-detail.component';
import { DependencyConditionUpdateComponent } from './dependency-condition-update.component';

@Injectable({ providedIn: 'root' })
export class DependencyConditionResolve implements Resolve<IDependencyCondition> {
  constructor(private service: DependencyConditionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDependencyCondition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dependencyCondition: HttpResponse<DependencyCondition>) => {
          if (dependencyCondition.body) {
            return of(dependencyCondition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DependencyCondition());
  }
}

export const dependencyConditionRoute: Routes = [
  {
    path: '',
    component: DependencyConditionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.dependencyCondition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DependencyConditionDetailComponent,
    resolve: {
      dependencyCondition: DependencyConditionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.dependencyCondition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DependencyConditionUpdateComponent,
    resolve: {
      dependencyCondition: DependencyConditionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.dependencyCondition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DependencyConditionUpdateComponent,
    resolve: {
      dependencyCondition: DependencyConditionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jDemoApp.dependencyCondition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

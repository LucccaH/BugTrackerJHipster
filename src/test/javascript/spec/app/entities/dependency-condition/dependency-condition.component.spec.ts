import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JDemoTestModule } from '../../../test.module';
import { DependencyConditionComponent } from 'app/entities/dependency-condition/dependency-condition.component';
import { DependencyConditionService } from 'app/entities/dependency-condition/dependency-condition.service';
import { DependencyCondition } from 'app/shared/model/dependency-condition.model';

describe('Component Tests', () => {
  describe('DependencyCondition Management Component', () => {
    let comp: DependencyConditionComponent;
    let fixture: ComponentFixture<DependencyConditionComponent>;
    let service: DependencyConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [DependencyConditionComponent],
      })
        .overrideTemplate(DependencyConditionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DependencyConditionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DependencyConditionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DependencyCondition(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dependencyConditions && comp.dependencyConditions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

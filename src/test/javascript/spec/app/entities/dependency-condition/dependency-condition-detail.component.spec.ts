import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { DependencyConditionDetailComponent } from 'app/entities/dependency-condition/dependency-condition-detail.component';
import { DependencyCondition } from 'app/shared/model/dependency-condition.model';

describe('Component Tests', () => {
  describe('DependencyCondition Management Detail Component', () => {
    let comp: DependencyConditionDetailComponent;
    let fixture: ComponentFixture<DependencyConditionDetailComponent>;
    const route = ({ data: of({ dependencyCondition: new DependencyCondition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [DependencyConditionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DependencyConditionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DependencyConditionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dependencyCondition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dependencyCondition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

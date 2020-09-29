import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { DependencyConditionUpdateComponent } from 'app/entities/dependency-condition/dependency-condition-update.component';
import { DependencyConditionService } from 'app/entities/dependency-condition/dependency-condition.service';
import { DependencyCondition } from 'app/shared/model/dependency-condition.model';

describe('Component Tests', () => {
  describe('DependencyCondition Management Update Component', () => {
    let comp: DependencyConditionUpdateComponent;
    let fixture: ComponentFixture<DependencyConditionUpdateComponent>;
    let service: DependencyConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [DependencyConditionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DependencyConditionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DependencyConditionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DependencyConditionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DependencyCondition(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DependencyCondition();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

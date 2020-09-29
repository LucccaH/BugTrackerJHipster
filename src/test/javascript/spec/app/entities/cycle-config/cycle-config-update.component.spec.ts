import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { CycleConfigUpdateComponent } from 'app/entities/cycle-config/cycle-config-update.component';
import { CycleConfigService } from 'app/entities/cycle-config/cycle-config.service';
import { CycleConfig } from 'app/shared/model/cycle-config.model';

describe('Component Tests', () => {
  describe('CycleConfig Management Update Component', () => {
    let comp: CycleConfigUpdateComponent;
    let fixture: ComponentFixture<CycleConfigUpdateComponent>;
    let service: CycleConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [CycleConfigUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CycleConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CycleConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CycleConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CycleConfig(123);
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
        const entity = new CycleConfig();
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

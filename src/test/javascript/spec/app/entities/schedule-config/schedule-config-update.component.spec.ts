import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { ScheduleConfigUpdateComponent } from 'app/entities/schedule-config/schedule-config-update.component';
import { ScheduleConfigService } from 'app/entities/schedule-config/schedule-config.service';
import { ScheduleConfig } from 'app/shared/model/schedule-config.model';

describe('Component Tests', () => {
  describe('ScheduleConfig Management Update Component', () => {
    let comp: ScheduleConfigUpdateComponent;
    let fixture: ComponentFixture<ScheduleConfigUpdateComponent>;
    let service: ScheduleConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [ScheduleConfigUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ScheduleConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScheduleConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScheduleConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScheduleConfig(123);
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
        const entity = new ScheduleConfig();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { AlarmConfigUpdateComponent } from 'app/entities/alarm-config/alarm-config-update.component';
import { AlarmConfigService } from 'app/entities/alarm-config/alarm-config.service';
import { AlarmConfig } from 'app/shared/model/alarm-config.model';

describe('Component Tests', () => {
  describe('AlarmConfig Management Update Component', () => {
    let comp: AlarmConfigUpdateComponent;
    let fixture: ComponentFixture<AlarmConfigUpdateComponent>;
    let service: AlarmConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [AlarmConfigUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlarmConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlarmConfig(123);
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
        const entity = new AlarmConfig();
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

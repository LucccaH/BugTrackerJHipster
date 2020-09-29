import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JDemoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ScheduleConfigDeleteDialogComponent } from 'app/entities/schedule-config/schedule-config-delete-dialog.component';
import { ScheduleConfigService } from 'app/entities/schedule-config/schedule-config.service';

describe('Component Tests', () => {
  describe('ScheduleConfig Management Delete Component', () => {
    let comp: ScheduleConfigDeleteDialogComponent;
    let fixture: ComponentFixture<ScheduleConfigDeleteDialogComponent>;
    let service: ScheduleConfigService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [ScheduleConfigDeleteDialogComponent],
      })
        .overrideTemplate(ScheduleConfigDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScheduleConfigDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScheduleConfigService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});

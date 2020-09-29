import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { ScheduleConfigDetailComponent } from 'app/entities/schedule-config/schedule-config-detail.component';
import { ScheduleConfig } from 'app/shared/model/schedule-config.model';

describe('Component Tests', () => {
  describe('ScheduleConfig Management Detail Component', () => {
    let comp: ScheduleConfigDetailComponent;
    let fixture: ComponentFixture<ScheduleConfigDetailComponent>;
    const route = ({ data: of({ scheduleConfig: new ScheduleConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [ScheduleConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ScheduleConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScheduleConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load scheduleConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scheduleConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

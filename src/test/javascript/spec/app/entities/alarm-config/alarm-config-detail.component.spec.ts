import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { AlarmConfigDetailComponent } from 'app/entities/alarm-config/alarm-config-detail.component';
import { AlarmConfig } from 'app/shared/model/alarm-config.model';

describe('Component Tests', () => {
  describe('AlarmConfig Management Detail Component', () => {
    let comp: AlarmConfigDetailComponent;
    let fixture: ComponentFixture<AlarmConfigDetailComponent>;
    const route = ({ data: of({ alarmConfig: new AlarmConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [AlarmConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlarmConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlarmConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alarmConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alarmConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

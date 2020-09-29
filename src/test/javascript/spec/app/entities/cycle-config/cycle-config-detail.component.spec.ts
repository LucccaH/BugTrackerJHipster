import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JDemoTestModule } from '../../../test.module';
import { CycleConfigDetailComponent } from 'app/entities/cycle-config/cycle-config-detail.component';
import { CycleConfig } from 'app/shared/model/cycle-config.model';

describe('Component Tests', () => {
  describe('CycleConfig Management Detail Component', () => {
    let comp: CycleConfigDetailComponent;
    let fixture: ComponentFixture<CycleConfigDetailComponent>;
    const route = ({ data: of({ cycleConfig: new CycleConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [CycleConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CycleConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CycleConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cycleConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cycleConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

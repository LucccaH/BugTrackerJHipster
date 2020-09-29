import { Moment } from 'moment';
import { CycleType } from 'app/shared/model/enumerations/cycle-type.model';

export interface ICycleConfig {
  id?: number;
  effectiveDate?: Moment;
  assignedDate?: Moment;
  assignedTime?: Moment;
  cycleEnum?: CycleType;
  conditionRootId?: number;
}

export class CycleConfig implements ICycleConfig {
  constructor(
    public id?: number,
    public effectiveDate?: Moment,
    public assignedDate?: Moment,
    public assignedTime?: Moment,
    public cycleEnum?: CycleType,
    public conditionRootId?: number
  ) {}
}

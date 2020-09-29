import { Moment } from 'moment';
import { DependencyTypeEnum } from 'app/shared/model/enumerations/dependency-type-enum.model';
import { OperatorEnum } from 'app/shared/model/enumerations/operator-enum.model';

export interface IDependencyCondition {
  id?: number;
  dependencyType?: DependencyTypeEnum;
  flagFiekdId?: string;
  assignedTime?: Moment;
  operator?: OperatorEnum;
  dependencyConditions?: IDependencyCondition[];
  rootId?: number;
}

export class DependencyCondition implements IDependencyCondition {
  constructor(
    public id?: number,
    public dependencyType?: DependencyTypeEnum,
    public flagFiekdId?: string,
    public assignedTime?: Moment,
    public operator?: OperatorEnum,
    public dependencyConditions?: IDependencyCondition[],
    public rootId?: number
  ) {}
}

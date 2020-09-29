import { Moment } from 'moment';
import { TaskStatus } from 'app/shared/model/enumerations/task-status.model';

export interface ITask {
  id?: number;
  taskVersionId?: number;
  taskDetail?: string;
  taskName?: string;
  dataType?: number;
  taskStatusEnum?: TaskStatus;
  createTime?: Moment;
  alarmConfigId?: number;
  scheduleConfigId?: number;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public taskVersionId?: number,
    public taskDetail?: string,
    public taskName?: string,
    public dataType?: number,
    public taskStatusEnum?: TaskStatus,
    public createTime?: Moment,
    public alarmConfigId?: number,
    public scheduleConfigId?: number
  ) {}
}

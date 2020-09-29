export interface IScheduleConfig {
  id?: number;
  privilige?: number;
  retryTime?: number;
  retryInterval?: number;
  scheduleCycleConfigId?: number;
}

export class ScheduleConfig implements IScheduleConfig {
  constructor(
    public id?: number,
    public privilige?: number,
    public retryTime?: number,
    public retryInterval?: number,
    public scheduleCycleConfigId?: number
  ) {}
}

export interface IAlarmConfig {
  id?: number;
  alarmMethodb?: string;
  accountInfo?: string;
}

export class AlarmConfig implements IAlarmConfig {
  constructor(public id?: number, public alarmMethodb?: string, public accountInfo?: string) {}
}

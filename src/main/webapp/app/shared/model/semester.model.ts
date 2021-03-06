import { Moment } from 'moment';
import { ISemesterGroup } from 'app/shared/model//semester-group.model';

export const enum SemesterNumber {
    NONE = 'NONE',
    ONE = 'ONE',
    TWO = 'TWO',
    THREE = 'THREE',
    FOUR = 'FOUR',
    FIVE = 'FIVE',
    SIX = 'SIX',
    SEVEN = 'SEVEN',
    EIGHT = 'EIGHT'
}

export interface ISemester {
    id?: number;
    semesterNumber?: SemesterNumber;
    semesterStartDate?: Moment;
    semesterEndDate?: Moment;
    semesterGroups?: ISemesterGroup[];
    courseYearId?: number;
}

export class Semester implements ISemester {
    constructor(
        public id?: number,
        public semesterNumber?: SemesterNumber,
        public semesterStartDate?: Moment,
        public semesterEndDate?: Moment,
        public semesterGroups?: ISemesterGroup[],
        public courseYearId?: number
    ) {}
}

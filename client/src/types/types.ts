export type statusType = "AVAILABLE" | "UNAVAILABLE";
export type fuelType = "PETROL" | "DIESEL" | "HYBRID" | "ELECTRIC";
export type transmissionType = "MANUAL" | "AUTOMATIC";
export type driveType = "REAR" | "FRONT" | "ALL_WHEEL";

export type ReservationInput = {
  startDate: Date;
  endDate: Date;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
};

export type EditReservationInput = {
  startDate: Date;
  endDate: Date;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  cost: number;
};

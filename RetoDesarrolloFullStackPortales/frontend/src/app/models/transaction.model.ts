export interface Transaction {
    id: number;
    gmf: boolean;
    transaction_date: Date;
    description: string;
    available_balance: number;
    movement_type: string;
    id_sender_account: string;
    id_receptor_account: string;
    transaction_type: string;
    transaction_value: number
}

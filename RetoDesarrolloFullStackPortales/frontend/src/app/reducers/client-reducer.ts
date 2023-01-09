
import { Client } from "../models/client.model";
export function manageClients(state:Client[]=[], action: any){
    switch (action.type) {
        case 'MANAGE_CLIENT':
            return [...state,action.payload];
            
        default:
            return state;
    }
}


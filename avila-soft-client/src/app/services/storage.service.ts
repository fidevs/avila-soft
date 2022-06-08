import { Injectable } from '@angular/core';
import { Storage } from '@capacitor/storage';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  public async saveObject<T>(key: string, value: T): Promise<T> {
    await Storage.set({ key, value: JSON.stringify(value) });
    return value;
  }

  public async getObject<T>(key: string): Promise<T> {
    const result =  await Storage.get({ key });
    return JSON.parse(result.value);
  }

  public async saveString(key: string, value: string): Promise<string> {
    await Storage.set({ key, value });
    return value;
  }

  public async getString(key: string): Promise<string> {
    const result =  await Storage.get({ key });
    return result.value;
  }

  public async removeItem(key: string) {
    await Storage.remove({ key });
  }
}

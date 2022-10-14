import { WebPlugin } from '@capacitor/core';

import type { SafeAreaPlugin } from './definitions';

export class SafeAreaWeb extends WebPlugin implements SafeAreaPlugin {
  async getInsets(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }
}

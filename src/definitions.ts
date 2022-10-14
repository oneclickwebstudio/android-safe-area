export interface SafeAreaPlugin {
  getInsets(): Promise<{ top: number, bottom: number }>;
}

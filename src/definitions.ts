/**
 *  Get insets of safe area
 * @return {Promise<{ top: number, bottom: number }>}
 *
 * @since 4.0.13
 */
export interface SafeAreaPlugin {
  getInsets(): Promise<{ top: number, bottom: number }>;
}

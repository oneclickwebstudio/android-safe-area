/**
 *  Get insets of safe area
 *
 *  
 * @return {Promise<{ top: number, bottom: number }>}
 *
 * @since 0.0.1
 */
export interface SafeAreaPlugin {
  getInsets(): Promise<{ top: number, bottom: number }>;
}

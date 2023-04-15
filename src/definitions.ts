/**
 *  Interface of insets
 */
export interface SafeAreaInsets {
  top: number;
  left: number;
  right: number;
  bottom: number;
}

/**
 *  Get insets of safe area
 *
 *
 * @return {Promise<{ top: number, bottom: number }>}
 *
 * @since 0.0.1
 */
export interface SafeAreaPlugin {
  getInsets(): Promise<SafeAreaInsets>;
}

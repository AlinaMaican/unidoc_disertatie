import {LanguageType} from "../type/language.type";

export class LanguageUtil {
  private static readonly _languageKey = 'BS_UI_LANGUAGE';

  static setLanguage(language: LanguageType) {
    localStorage.setItem(this._languageKey, language);
  }

  static getLanguage(): LanguageType {
    return <LanguageType>localStorage.getItem(this._languageKey) || LanguageType.EN;
  }
}

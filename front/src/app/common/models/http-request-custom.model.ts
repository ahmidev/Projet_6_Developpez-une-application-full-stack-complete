export interface HttpHeaders {
    normalizedNames: Record<string, string>;
    lazyUpdate: null | any;
    headers: Record<string, string[]>;
  }
  
  export interface HttpParams {
    updates: null | any;
    cloneFrom: null | any;
    encoder: any;
    map: Record<string, string[]>;
  }
  
  export interface HttpContext {
    map: Record<string, any>;
  }
  
  export interface HttpRequestCustom {
    url: string;
    body: any; 
    reportProgress: boolean;
    withCredentials: boolean;
    responseType: 'json';
    method: string;
    headers: HttpHeaders;
    context: HttpContext;
    params: HttpParams;
    urlWithParams: string;
  }
  
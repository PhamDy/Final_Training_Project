
export class User{

  public id: number;
  public name: string;
  public email : string;
  public password: string;
  public role : string;
  public authStatus : string;


  constructor(id?: number,name?: string, email?: string,  password?: string,role?: string, authStatus?:string){
        this.id = id || 0;
        this.name = name || '';
        this.email = email || '';
        this.password = password || '';
        this.role = role || '';
        this.authStatus = authStatus || '';
  }

}

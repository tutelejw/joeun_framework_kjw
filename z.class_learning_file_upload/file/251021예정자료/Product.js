

import { Link, Route, Switch, useRouteMatch } from 'react-router-dom';
import Product01 from '/Product01';
import Product02 from '/Product02';
import Product03 from '/Product03';
import NotFound from './commonCompone/NotFound';

const Product = () => {

console.log('Product');

// www.reactrouter.com : Nesting 참조.
// 1. path, url 값들
// Destructuring 받아 확인
// path: <Route>의 path prop과 비교해서 매칭된 그대로 분할 할당
let { path, url } = useRouteMatch();
// url: <Link>나 <Route>의 path 값과 url 을 확인할 수 있다.
console.log(path);
console.log(url);

return (
<div className="ViewGood">

{/* Sub Routing(Nesting) 이해와 적용.

1) App.js 의 <Route path='/product'><Product/></Route>
Product Component 포함된다 이해 했으면,

2) http://localhost:3000/product/
하나 => http://localhost:3000/product/<Route>

===> Display 될 컴포넌트 이다.

==> <Route path={path+'/product01'}><Product01/></Route>

등등 => Product01 Component 포함이 보여진다.

3) 확인 => http://localhost:3000/product/ : <Route path=''>

=> Product Component
4) 확인 => http://localhost:3000/product/abc : <Route path='*'>
=> NotFound 확인이다. : http://localhost:3000/product/abc
5) 확인 => NotFound 아님을 이해 할 것

*/}

<Link to={url+'/product01'}>Product01</Link> &nbsp; &nbsp; &nbsp; &nbsp;
<Link to={url+'/product02'}>Product02</Link> &nbsp; &nbsp; &nbsp; &nbsp;
<Link to={url+'/product03'}>Product03</Link> &nbsp; &nbsp; &nbsp; &nbsp;

<Switch>




<Route path={path+'/product01'}><Product01/></Route>
<Route path={path+'/product02'}><Product02/></Route>
<Route path={path+'/product03'}><Product03/></Route>
<Route path={path+'/*'}><NotFound/></Route>
</Switch>

</div>
);
};

export default Product;


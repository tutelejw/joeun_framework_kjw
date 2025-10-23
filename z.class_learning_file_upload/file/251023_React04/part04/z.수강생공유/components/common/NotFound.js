
import { useLocation } from 'react-router-dom';
import { Container } from 'react-bootstrap';

const NotFound = () => {

  console.log("NotFound");

  /*
    >> 확인 : http://localhost:3000/abc
    - www.reactrouter.com : No Match(404) 참조함.
  */
  // useLocation() : 현재 URL의 location 객체를 반환함.
  const location = useLocation();
  // location.pathname : 현재 URL의 경로를 반환함. 구조분해 할당 사용.
  const { pathname } = useLocation(); 
  return (


    <Container>

      <h3>
        요청하신 Page 존재하지 않습니다. {location.pathname}
        <br/>
        요청하신 Page 존재하지 않습니다. {pathname}
      </h3>

    </Container>
  );
};

export default NotFound;
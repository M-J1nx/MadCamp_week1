
# 🥘*PokéMenu*
https://drive.google.com/file/d/1oAR2GdMz5-x1qEAiKagJfla2Zp3r6oA9/view?usp=sharing

*by. 1분반 박현규, 정민서*

---

## 1. Introduce

오늘도 점심 메뉴를 고민하는 당신. 포켓몬이 추천해주는 메뉴는 어떤가요?

---

## 2. Member

- 박현규 - KAIST 전기및전자공학부 20학번
- 정민서 - 숙명여대 소프트웨어학부 22학번

---

## 3. Environment

- Language : Kotlin, JSON
- OS : Android
    - `minSdkVersion` : 30
    - `compileSdk` : 34
    - `targetSdk` : 34
- IDE : Android Studio
- Target Device : Galaxy S10e

---

## 4. Application

### A. Tab1 : 전화번호

**[ Major features ]**

- 기본적으로 저장된 포켓몬들의 연락처를 볼 수 있습니다
- ‘ㄱ’ 부터 오름차순으로 정렬되어, 원하는 ‘포켓몬의 연락처를 쉽게 찾아볼 수 있습니다
- 찾고 싶은 이름을 입력하면 입력한 정보와 일치하는 포켓몬을 보여줍니다
- 정보를 더 자세히 보고 싶은 포켓몬의 연락처를 클릭하면 세부 정보를 볼 수 있습니다
![1000009680](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/3bdc6b1f-e3ef-45b9-b916-675d7d11f5eb)
![1000009681](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/271905c8-1f15-4e8b-9714-e294f023582e)
![1000009682](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/f4ade1a5-84cc-42e2-aa65-d7764af0b25c)


**[ 기술 설명 ]**

1. 포켓몬 연락처 열람
    - 포켓몬의 기본 연락처 정보가 저장된 JSON 파일을 전화번호에 해당하는 fragment 에서 파싱해 가져옵니다. 이 때, 오름차순 정렬을 위해 파싱한 데이터를 정렬하여 반환합니다.
    - RecyclerView를 설정할 때, 파싱해서 가져온 데이터를 Adapter에 적용시켜 출력합니다.
2. 연락처 세부 정보 열람 
    - 각 객체에 클릭 이벤트를 설정하고, 데이터를 설정하는 코드에서 클릭된 객체의 데이터를 받아와 설정합니다.
3. 연락처 검색
    - SearView 위젯을 사용하여 사용자로부터 검색을 원하는 포켓몬의 이름을 받습니다.
    - 데이터를 필터링하는 함수를 설정하여 만약 입력받은 데이터가 없거나 빈칸인 상태라면 모든 전화번호를 출력하고, 아니라면 모든 전화번호가 담긴 다중 리스트 중 이름만 추출하여 비교해 출력했습니다.

---

### B. Tab2 : 갤러리

**[ Major features ]**

- 기본적으로 앱 내부 파일에 저장 되어있는 사진들을 볼 수 있습니다.
- 각 사진을 터치할 때 사진을 확대하여 보여주는 화면이 나옵니다.
- 사진을 확대했을 때 아래의 삭제 버튼을 눌러 해당 사진을 간편하게 삭제할 수 있습니다.
- Access Gallery를 눌러 핸드폰 내부 갤러리에 접근하여 이미지를 받아올 수 있습니다.
- 이미지 순서를 셔플 기능을 통하여 바꾸어 볼 수 있습니다.

![Untitled](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/28098350-9554-4edc-b0f1-d72a91281f7e)
![Untitled 1](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/f5dfd37e-3774-45ab-9097-e2034caf763d)

**[ 기술 설명 ]**

1. 사진 열람
    - 사진이 ID로 주어졌을 때, URI로 변환하여 리스트에 저장하고 인덱스 데이터를 어뎁터로 보내 recyclerView로 화면 상에 나타냅니다.
2. 사진 확대 및 삭제
    - 각 사진의 onClick 이벤트를 받아 사진을 터치했을때 intent를 통하여 SubActivity를 실행하고 인덱스 데이터를 넘겨주어 확대합니다.
    - 아래의 삭제 버튼을 누르면 SubActivity가 종료되고 해당 사진의 인덱스를 넘겨주어 이미지 정보 리스트의 해당 사진을 지웁니다.
3. 갤러리 이미지 받기
    - 외장저장소 Read permission을 받아 휴대폰 갤러리를 열고 그 안의 사진 정보를 URI로 받아와 이미지 정보 리스트에 추가합니다. 리스트에 추가된 이미지는 여타 이미지와 같이 동작하게 됩니다.
4. 셔플
    - 이미지 정보 리스트 데이터를 직접 셔플하고 업데이트하여 이미지 순서를 변경하며 열람할 수 있습니다.

---

### C. Tab3 : 점메추 (점심 메뉴 추천)

**C-1. 메뉴 카테고리 추천**

**[ Major features ]**

1. 카테고리 
    - 한식, 중식, 일식, 양식, 분식, 간편식 중 음식 카테고리를 애니메이션과 함께 랜덤으로 선택을 해줍니다.
2. 메뉴
    - 카테고리를 결정한 경우 해당 카테고리의 메뉴 리스트를 랜덤으로 골라주는 C-2 Fragment로 이동합니다.
    - 카테고리를 정하지 않은 상태로 메뉴를 누를 경우 카테고리가 결정되지 않은 상태에서 랜덤으로 뽑게 됩니다.
3. 메뉴추가
    - 내장 리스트에 원하는 음식이 없는 경우 사용자가 직접 메뉴를 카테고리를 선택하고 추가할 수 있습니다. URL을 추가하여 사진도 첨부할 수 있습니다.
4. 도망가기
    - 포켓볼이 열려있는 경우, 초기화를 시켜주며 두 번 터치할 경우 앱이 종료됩니다.

![nse-6343702799623513846-1000009684](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/98870268-250c-4d64-8072-7af91432d162)
![1000009683](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/809c038e-0054-4fe7-82d9-924a89cf3e0c)
![RecommendCategory](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/d62954d8-7a77-4db1-a1b6-ba9af72d26ba)
![1000009691](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/5c56edfb-c609-47ec-9886-2582828bc105)


**[ 기술 설명 ]**

1. 카테고리
    - ObjectAnimator을 사용하여 포켓볼의 튕기는 모션을 구현하였으며 이미지의 Visiblity를 조정하여 메뉴를 나타내는 상태를 구현했습니다.
    - 6개의 카테고리 중 임의의 값을 와서 보여줍니다.
2. 메뉴
    - 3번 탭안에서 추가적인 Fragment를 만들어 해당 버튼이 눌리면 넘어갈 수 있도록 하였습니다.
    - 카테고리를 결정했는지에 따라 추천의 범위가 조절됩니다.
3. 메뉴추가
    - 사용자에게 음식 이름, 타입, 음식 설명, 음식의 사진 URL을 입력받습니다.
    - 사용자의 메뉴 추가에 도움을 주기 위해 기존의 데이터에서 음식 이름을 추출해와 지정하였고, 사용자가 메뉴 추가 버튼을 누르면 사용자의 입력값 또한 확장시켜 저장함으로써 데이터를 업데이트 했습니다.
    - 사용자에게 받은 데이터를 intent를 통해 넘겨 이후의 메뉴 추천 탭에서 엑세스 할 수 있도록 했습니다.
4. 도망간다
    - 버튼을 2000L 안에 두 번 누르면 double tab Flag가 바뀌어 MainActivity를 종료하도록 하였습니다.

**C-2. 메뉴 추천**

**[ Major features ]**

- 최초 접속 시 랜덤하게 메뉴를 하나 선정해서 띄웁니다.
- 빨간색 새로고침 버튼을 누르면 다른 메뉴를 랜덤하게 추천 받을 수 있습니다
- 초록색 체크 버튼을 누르면 선택 횟수를 1씩 증가시킬 수 있습니다.
- 우측 하단의 이상해씨를 누르면 선택 횟수가 초기화 됩니다.

![1000009689](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/72445ea3-fc9d-48cf-ae6c-12ba84a8944b)
![1000009688](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/3b1027ef-f28a-493b-8e77-1466c0a807a1)
![1000009690](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/825cad70-851f-4b98-8933-cb69a0f2c9fc)
![nse-2155112819528536747-1000009685](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/0f0ae5ea-14fd-4f1e-a733-e01c1a3d9a59)
![nse-8315575435238767393-1000009687](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/609b6196-ceba-4efd-a4c5-bfa3939715f7)
![nse-6636214531976467944-1000009686](https://github.com/M-J1nx/MadCamp_week1_PokeMenu/assets/104704651/dedeac0f-006f-4f79-a314-f7466082d110)


**[ 기술 설명 ]**

1. 메뉴 선정
    - 음식에 대한 정보가 들어있는 JSON 파일을 파싱해 가져온 후, 앞서 추가한 메뉴를 다중 리스트에 확장시켜 추가합니다.
    - 새로 고침 할 때마다 리스트에서 음식의 번호, 이름, 타입, 선택 횟수, 음식 소개, 사진을 가져와서 대체해 넣습니다. 사진의 경우 URL을 통해 imageSource를 받아 지정해서 새로고침 할 때마다 인터넷에서 로드를 하게 했습니다 .
2. 선택 횟수
    - 선택 횟수를 JSON 파일에서 파싱한 후, 수정을 위해 따로 리스트화 했습니다.
    - 음식의 번호를 통해 인덱스에 접근할 수 있으며, 체크 버튼을 누를 때 마다 값을 1씩 증가시키고, 값만큼 반복문으로 ★을 출력하여 선택 횟수를 표현했습니다.
    - 이상해씨를 누르면 리스트의 음식 인덱스에 해당하는 값을 0으로 설정해줌으로써 횟수를 초기화 시켜주었습니다. 

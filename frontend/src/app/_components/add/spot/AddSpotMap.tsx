'use client';

import { useContext, useEffect, useRef, useState } from 'react';
import { Container, Marker, NaverMap, useNavermaps } from 'react-naver-maps';
import AddTravelSearchResult from '../AddTravelSearchResult';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { PlaceContext } from '@/context/travel/place/PlaceContext';

import ToastMsg from '../../ui/toast/ToastMsg';

import { getMap } from '../common/action';
import { basicMarker } from '../../naver-map/markers/basicMarker';
import { isEqualPlace } from '@/context/travel/utils';

const defaultCenter = {
  lat: 37.5135869,
  lng: 127.0621708,
};

export default function AddSpotMap() {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);
  const markerRef = useRef<naver.maps.Marker | null>(null);

  const [error, setError] = useState('');
  const [inform, setInform] = useState('');

  const [addTravelMapState, dispatchAddTravelMap] =
    useContext(AddTravelMapContext);
  const [placeState] = useContext(PlaceContext);

  const { current, selectedPlace } = addTravelMapState;

  useEffect(() => {
    if (mapRef.current && current.type === 'spot') {
      mapRef.current.autoResize();
      mapRef.current.panTo(current.data.coords);
    }
  }, [current]);

  if (placeState.type === 'course') {
    return null;
  }

  if (current.type === 'course') {
    return null;
  }

  const currentPlace = current.data;

  async function handleClick() {
    setError('');
    setInform('');

    if (isEqualPlace(currentPlace, selectedPlace)) {
      dispatchAddTravelMap({ type: 'UNSELECT_PLACE' });
      setInform('');
      return;
    }

    try {
      const { name, address, coords } = currentPlace;
      const imageUrl = await getMap(name, address, coords);

      if (imageUrl.status === 'failed') {
        throw new Error(imageUrl.error);
      }

      dispatchAddTravelMap({
        type: 'SELECT_PLACE',
        payload: {
          name,
          address,
          coords,
          mapImageUrl: imageUrl.data,
        },
      });

      dispatchAddTravelMap({
        type: 'SET_CURRENT_PLACE',
        payload: {
          type: 'spot',
          data: {
            name,
            address,
            coords,
            mapImageUrl: imageUrl.data,
          },
        },
      });
      setInform('완료를 눌러 장소를 입력하세요.');
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      }
      console.error(error);
    } finally {
      setTimeout(() => setError(''), 2000);
      setTimeout(() => setInform(''), 2000);
    }
  }

  const { lat, lng } = currentPlace.coords;

  return (
    <section className="flex flex-col grow">
      <hr className="my-4" />
      <section className="relative grow">
        <div className="absolute w-full h-full">
          <Container
            style={{ position: 'absolute', width: '100%', height: '100%' }}
          >
            <NaverMap defaultCenter={defaultCenter} ref={mapRef}>
              {lng !== 0 && lat !== 0 && (
                <Marker
                  ref={markerRef}
                  title={currentPlace.name}
                  position={currentPlace.coords}
                  icon={basicMarker(
                    currentPlace.name,
                    isEqualPlace(selectedPlace, currentPlace),
                  )}
                  onClick={handleClick}
                ></Marker>
              )}
            </NaverMap>
          </Container>
        </div>
        {addTravelMapState.isSearchResultOpen && <AddTravelSearchResult />}
        {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
        {inform && <ToastMsg id={Date.now()} title={inform} timer={2000} />}
      </section>
    </section>
  );
}
